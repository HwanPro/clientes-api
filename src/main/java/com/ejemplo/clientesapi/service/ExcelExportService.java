package com.ejemplo.clientesapi.service;

import com.ejemplo.clientesapi.model.Cliente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {
    
    public ByteArrayInputStream exportarClientesAExcel(List<Cliente> clientes) throws IOException {
        String[] columnas = {"ID Cliente", "DNI", "Nombres", "Apellido Paterno", "Apellido Materno", 
                           "Código Verificación", "Fecha Consulta"};
        
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Clientes");
            
            // Crear estilo para el encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Llenar datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            int rowNum = 1;
            for (Cliente cliente : clientes) {
                Row row = sheet.createRow(rowNum++);
                
                // Corregir: usar getId() en lugar de getUsuario().getId()
                row.createCell(0).setCellValue(cliente.getId() != null ? cliente.getId() : 0);
                row.createCell(1).setCellValue(cliente.getDni() != null ? cliente.getDni() : "");
                row.createCell(2).setCellValue(cliente.getNombres() != null ? cliente.getNombres() : "");
                row.createCell(3).setCellValue(cliente.getApellidoPaterno() != null ? cliente.getApellidoPaterno() : "");
                row.createCell(4).setCellValue(cliente.getApellidoMaterno() != null ? cliente.getApellidoMaterno() : "");
                row.createCell(5).setCellValue(cliente.getCodigoVerificacion() != null ? cliente.getCodigoVerificacion() : "");
                row.createCell(6).setCellValue(cliente.getFechaConsulta() != null ? cliente.getFechaConsulta().format(formatter) : "");
            }
            
            // Ajustar ancho de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}