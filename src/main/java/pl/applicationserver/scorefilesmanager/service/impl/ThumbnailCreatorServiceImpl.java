package pl.applicationserver.scorefilesmanager.service.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.stereotype.Service;
import pl.applicationserver.scorefilesmanager.service.ThumbnailCreatorService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Service
public class ThumbnailCreatorServiceImpl implements ThumbnailCreatorService {


//    @Override
//    public String createThumbnailBase64(SAFileMetadata fileMetadata) {
//        DownloadedFile file = fileService.createDownloadedFile(fileMetadata.getFileName());
//        byte[] fileBuff = Base64.getDecoder().decode(file.getContent());
//        InputStream inputStream = new ByteArrayInputStream(fileBuff);
//        try {
//            PDDocument doc = PDDocument.load(inputStream);
//            PDPage page = (PDPage)doc.getDocumentCatalog().getAllPages().get(0);
//            BufferedImage bufferedImage = page.convertToImage(1, 150);
//            OutputStream outputStream = new ByteArrayOutputStream();
//            ImageIO.write(bufferedImage, "png", outputStream);
//            byte[] output = new byte[0];
//            outputStream.write(output);
//            return Base64.getEncoder().encodeToString(output);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public String createThumbnailBase64(byte[] pdfFileContent) {
        InputStream inputStream = new ByteArrayInputStream(pdfFileContent);
        try {
            PDDocument doc = PDDocument.load(inputStream);
            PDFRenderer renderer = new PDFRenderer(doc);
            BufferedImage bufferedImage = renderer.renderImageWithDPI(0,300,ImageType.RGB);
            bufferedImage = resize(bufferedImage, 200,300);
            OutputStream outputStream = new ByteArrayOutputStream();
            ImageIOUtil.writeImage(bufferedImage, "png", outputStream);
            byte[] output = ((ByteArrayOutputStream) outputStream).toByteArray();
            return Base64.getEncoder().encodeToString(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
