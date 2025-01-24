package com.coursework.notificationservice.service

import com.coursework.notificationservice.dto.TripDTO
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.math.BigDecimal
import java.time.format.DateTimeFormatter
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset

@Service
class PdfService {

    fun generatePdf(trips: List<TripDTO>): ByteArray {
        val document = PDDocument()
        val page = PDPage()
        document.addPage(page)

        var contentStream = PDPageContentStream(document, page)

        // Шапка документа
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12f)
        contentStream.beginText()
        contentStream.newLineAtOffset(25f, 750f)
        contentStream.showText("Trip Analytics Report")
        contentStream.endText()

        // Добавление графика
        val chartImageBytes = generateChartImage(trips)
        val chartImage = PDImageXObject.createFromByteArray(document, chartImageBytes, "Chart Image")
        contentStream.drawImage(chartImage, 50f, 500f, 500f, 250f)

        // Добавление статистики
        addStatistics(contentStream, trips, yPosition = 470f)

        contentStream.close()

        // Добавление таблицы
        contentStream = PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)
        var yPosition = 450f // Начало таблицы

        val headers = listOf(
            "ID", "Client", "Driver", "Car", "Company", "Tariff",
            "D.Rate", "C.Rate", "Service", "Price", "Start", "End"
        )

        // Рисуем заголовки таблицы
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7f) // Еще меньший шрифт
        drawRow(contentStream, headers, yPosition, cellWidth = 60f, isHeader = true)

        yPosition -= 10f // Переход на следующую строку

        // Данные таблицы
        contentStream.setFont(PDType1Font.HELVETICA, 7f) // Еще меньший шрифт
        trips.forEach { trip ->
            if (yPosition < 50f) {
                // Если места на странице не хватает, создаем новую страницу
                contentStream.close()
                val newPage = PDPage()
                document.addPage(newPage)
                contentStream = PDPageContentStream(document, newPage, PDPageContentStream.AppendMode.APPEND, true)
                contentStream.setFont(PDType1Font.HELVETICA, 7f)
                yPosition = 750f

                // Перерисовка заголовков на новой странице
                drawRow(contentStream, headers, yPosition, cellWidth = 60f, isHeader = true)
                yPosition -= 10f
            }

            // Преобразуем объект TripDTO в строку
            val rowData = listOf(
                trip.id.toString(),
                trip.clientId.toString(),
                trip.driverId.toString(),
                trip.carId.toString(),
                trip.companyId.toString(),
                trip.tariffId.toString(),
                trip.driverRate?.toString() ?: "N/A",
                trip.clientRate?.toString() ?: "N/A",
                trip.service.take(10), // Ограничение длины строки
                "%.2f".format(trip.price), // Уменьшение до 2 знаков после запятой
                trip.startDate.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")),
                trip.endDate.format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
            )

            drawRow(contentStream, rowData, yPosition, cellWidth = 60f, isHeader = false)
            yPosition -= 10f // Переход к следующей строке
        }

        contentStream.close()

        // Сохранение в байтовый массив
        val outputStream = ByteArrayOutputStream()
        document.save(outputStream)
        document.close()

        return outputStream.toByteArray()
    }

    /**
     * Отрисовка строки таблицы
     */
    private fun drawRow(
        contentStream: PDPageContentStream,
        rowData: List<String>,
        yPosition: Float,
        cellWidth: Float,
        isHeader: Boolean
    ) {
        var xPosition = 25f // Начальная позиция по X

        contentStream.beginText()
        contentStream.newLineAtOffset(xPosition, yPosition)

        rowData.forEach { cellData ->
            contentStream.showText(cellData)
            xPosition += cellWidth
            contentStream.newLineAtOffset(cellWidth, 0f)
        }

        contentStream.endText()
    }




    private fun addStatistics(contentStream: PDPageContentStream, trips: List<TripDTO>, yPosition: Float) {
        val totalPrice = trips.sumOf { it.price.toDouble() }
        val averagePrice = totalPrice / trips.size

        contentStream.beginText()
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12f)
        contentStream.newLineAtOffset(50f, yPosition)
        contentStream.showText("Total Price: $totalPrice")
        contentStream.newLineAtOffset(0f, -15f)
        contentStream.showText("Average Price: $averagePrice")
        contentStream.endText()
    }

    private fun generateChartImage(trips: List<TripDTO>): ByteArray {
        val dataset = DefaultCategoryDataset()

        // Заполнение данных для графика
        trips.forEach { trip ->
            dataset.addValue(trip.price.toDouble(), "Price", trip.service)
        }

        val chart: JFreeChart = ChartFactory.createBarChart(
            "Trip Prices by Service",
            "Service",
            "Price",
            dataset
        )

        val chartImageStream = ByteArrayOutputStream()
        ChartUtils.writeChartAsPNG(chartImageStream, chart, 600, 400)
        return chartImageStream.toByteArray()
    }
}

