package ru.levelp;

/**
 * Created by Мария on 07.10.2016.
 */
import ru.levelp.api.Methods;
import ru.levelp.api.RequestContainer;
import ru.levelp.api.ResponseContainer;
import ru.levelp.dao.BackupDatabase;
import ru.levelp.entities.BackupInfo;
import ru.levelp.utils.JsonUtil;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private volatile boolean running = true;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {

        try {
            while (running) {
                String jsonRequest = reader.readLine();
                if (jsonRequest != null) {
                    RequestContainer request = JsonUtil.getInstance().jsonToRequest(jsonRequest);
                    onRequest(request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onRequest(RequestContainer request) throws IOException {
        String method = request.getMethod();
        if (method.equals(Methods.UPLOAD)) {
            receiveFile(request.getId(), request.getBackupName());
            BackupInfo b = new BackupInfo();
            b.setId(request.getId());
            b.setName(request.getBackupName());
            b.setCreated(request.getTs());
            BackupDatabase.getInstance().add(b);
            disconnect();
        } else if (method.equals(Methods.GET_HISTORY)) {
            //отдельный thread
            System.out.println(BackupDatabase.getInstance().getAll());
        } else if (method.equals(Methods.GET_BACKUP)) {

        }
    }

    private void receiveFile(int requestId, String backupName) {
        int backupId = (int) (Math.random() * Integer.MAX_VALUE);
        File dir = new File("backups", String.valueOf(backupId));
        //Нужно проверить, занят ли этот id другим бэкапом в базе
//        if (dir.exists()) {
//            //нужен другой backupId
//        }
        dir.mkdir();
        File outputFile = new File(dir.getPath(), backupName);
        try {
            outputFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            while (true) {
                String jsonFilePart = reader.readLine();
                RequestContainer filePart = JsonUtil.getInstance().jsonToRequest(jsonFilePart);
                if (filePart.getMethod().equals(Methods.FILE_PART)) {
                    if (filePart.getBytes() == null) {
                        break;
                    }
                    outputStream.write(filePart.getBytes());
                    outputStream.flush();
                }
            }
            outputStream.close();
            ResponseContainer response = new ResponseContainer(requestId, Methods.UPLOAD);
            response.setBackupId(backupId);
            sendResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
            //удалить созданные папку и файл
            //ответить пользователю, что произошла ошибка (тоже ResponseContainer)
        }
    }

    private void sendResponse(ResponseContainer response) {
        String jsonResponse = JsonUtil.getInstance().responseToJson(response);
        writer.println(jsonResponse);
        writer.flush();
    }

    private void disconnect() throws IOException {
        reader.close();
        writer.close();
        socket.close();
        running = false;
    }
}