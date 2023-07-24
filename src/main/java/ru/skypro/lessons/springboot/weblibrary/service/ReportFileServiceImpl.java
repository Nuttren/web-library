package ru.skypro.lessons.springboot.weblibrary.service;

import ru.skypro.lessons.springboot.weblibrary.dto.ReportDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class ReportFileServiceImpl implements ReportFileService{

    private static  final String REPORT_DIRECTORY = "C:/Reports"; // Замените на путь к директории, где будет сохраняться отчет

    @Override
    public void saveReportToFile(ReportDTO reportDTO, String fileName) {
        String filePath = REPORT_DIRECTORY + "/" + fileName;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            // Сериализация отчета в файл
            objectOutputStream.writeObject(reportDTO);

            System.out.println("Отчет сохранен в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении отчета в файл: " + e.getMessage());
        }
    }
}

