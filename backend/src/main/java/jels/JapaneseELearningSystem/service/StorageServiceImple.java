package jels.JapaneseELearningSystem.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.*;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.tika.exception.TikaException;
//import org.apache.tika.metadata.Metadata;
//import org.apache.tika.parser.ParseContext;

//import org.apache.tika.sax.BodyContentHandler;
//import org.apache.tika.sax.ToXMLContentHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import jels.JapaneseELearningSystem.entity.DocxContent;


@Service
public class StorageServiceImple implements StorageService {

	private Path root;

	public StorageServiceImple() throws IOException {

		root = Paths.get("").resolve("src").resolve("main").resolve("resources").resolve("static");

		if (!Files.exists(root.resolve("video")) || !Files.exists(root.resolve("image"))
				|| !Files.exists(root.resolve("other"))) {
			// Files.createDirectories(root);
			Files.createDirectories(root.resolve("media").resolve("video"));
			Files.createDirectories(root.resolve("media").resolve("image"));
			Files.createDirectories(root.resolve("media").resolve("other"));
		}

	}

	@Override
	public String saveFile(MultipartFile file) {
		Path filePath;
		try {
			String fileName = Instant.now().getEpochSecond() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

			switch (fileName.substring(fileName.lastIndexOf("."))) {
			case ".mp4":
			case ".avi":
			case ".flv":
				Files.copy(file.getInputStream(), this.root.resolve("media").resolve("video").resolve(fileName));
				filePath = Paths.get("media").resolve("video").resolve(fileName);
				break;
			case ".jpg":
			case ".png":
			case ".jpeg":
				Files.copy(file.getInputStream(), this.root.resolve("media").resolve("image").resolve(fileName));
				filePath = Paths.get("media").resolve("image").resolve(fileName);
				break;

			default:

				Files.copy(file.getInputStream(), this.root.resolve("media").resolve("other").resolve(fileName));
				filePath = Paths.get("media").resolve("other").resolve(fileName);
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Could not store file.");
		}
		return filePath.toString();

	}

	//

	@Override
	public byte[] load(String fileName) {

		byte[] retBytes = null;
		Path filePath;

		try {
			// Path filePath = this.storagePath.resolve(filePath);
			filePath = root.resolve(fileName);

			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() && resource.isReadable()) {
				retBytes = StreamUtils.copyToByteArray(resource.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retBytes;

	}

	@Override
	public boolean delete(String fileName) {
		Path filePath;
//		System.out.println(root);
//		System.out.println(fileName);
		if (fileName != null && fileName != "") {
			try {

				filePath = root.resolve(fileName);
				System.out.println(filePath);

				Files.delete(filePath);
				System.out.println("deleted");
				return true;

			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}
//	@Override
//	public List<String> saveMultipleFile(MultipartFile[] files) {
//		List<String> filePath= new ArrayList<>();
//		try {
//			for (MultipartFile file : files) {
//				String fileName= Instant.now().getEpochSecond() + "_"
//						+ StringUtils.cleanPath(file.getOriginalFilename());
//				Files.copy(file.getInputStream(),this.root.resolve(fileName));
//				
//				filePath.add(root+ fileName);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new RuntimeException("Could not store file.");
//		}
//		return filePath;
//	}

	@Override
	public String update(String oldFileName, MultipartFile file) {

		Path filePath;
		try {
			String newFileName = Instant.now().getEpochSecond() + "_"
					+ StringUtils.cleanPath(file.getOriginalFilename());

			switch (newFileName.substring(newFileName.lastIndexOf("."))) {
			case ".mp4":
			case ".avi":
			case ".flv":
				this.delete(oldFileName);
				Files.copy(file.getInputStream(), this.root.resolve("media").resolve("video").resolve(newFileName));
				filePath = Paths.get("media").resolve("video").resolve(newFileName);
				break;
			case ".jpg":
			case ".png":
			case ".jpeg":
				this.delete(oldFileName);
				Files.copy(file.getInputStream(), this.root.resolve("media").resolve("image").resolve(newFileName));
				filePath = Paths.get("media").resolve("image").resolve(newFileName);
				break;

			default:
				this.delete(oldFileName);
				Files.copy(file.getInputStream(), this.root.resolve("media").resolve("other").resolve(newFileName));
				filePath = Paths.get("media").resolve("other").resolve(newFileName);
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return null;
			
		}
		System.out.println(file);
		return filePath.toString();
	}

//	@Override
//	  public  String readDocxFile(String fileName) {
//		String content;
//		try {
//			  String filepath = this.root+"/"+fileName;
//		        FileInputStream fis = new FileInputStream(new File(filepath));
//		        XWPFDocument docx = new XWPFDocument(fis);
//		        XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
//		        content = extractor.getText();
//		        
//		        fis.close();
//		        return content;
//		} catch (Exception e) {
//			// TODO: handle exception
//			return null;
//		}
//		
//	        
//	    }


	@Override
	public DocxContent readDocxFile(String fileName) {
		StringBuilder textContent = new StringBuilder();
		DocxContent docxContent = new DocxContent();
		String filepath = this.root+"/"+fileName;
		try {
			InputStream fis = new FileInputStream(filepath);
			XWPFDocument doc = new XWPFDocument(fis);
			//System.out.println(doc.getBodyElements());
			
			//int imagePosition = 0;
			doc.getParagraphs().forEach(paragraph->textContent.append(paragraph.getText()).append("\n"));
			for (XWPFParagraph paragraph : doc.getParagraphs()) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					List<XWPFPicture> pictures = run.getEmbeddedPictures();
//					if(pictures.isEmpty()) {
//						textContent.append(run.getText(0)).append("\n");
//					}else {
						for(XWPFPicture picture : pictures) {
							XWPFPictureData pictureData = picture.getPictureData();
							String contentType = pictureData.getPackagePart().getContentType();
							String imageBase64 = convertImageToBase64(pictureData.getData(), contentType);
							docxContent.addImage(imageBase64);
							
						}
					//}
				}
			}
			docxContent.setTextContent(textContent.toString());
//			doc.getParagraphs().forEach(paragraph->textContent.append(paragraph.getText()).append("\n"));
//			for(XWPFPictureData pictureData : doc.getAllPictures()
//					) {
//				String contentType = pictureData.getPackagePart().getContentType();
//				String imageBase64 = convertImageToBase64(pictureData.getData(), contentType);
//				docxContent.addImage(imageBase64);
//			}
//			docxContent.setTextContent(textContent.toString());
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		return docxContent;
	}
	private String convertImageToBase64(byte[] imageData, String contentType) {
        String base64Image = "";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Convert image to Base64
            String imageFormat = contentType.split("/")[1];
            base64Image = "data:image/" + imageFormat + ";base64," + Base64.getEncoder().encodeToString(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
//	@Override
//	public DocxContent readDocxFile(String fileName) {
//		//StringBuilder textContent = new StringBuilder();
//		DocxContent docxContent = new DocxContent();
//		String filepath = this.root+"/"+fileName;
//		try {
//			InputStream fis = new FileInputStream(filepath);
//			XWPFDocument doc = new XWPFDocument(fis);
//			
//			List<XWPFParagraph> paragraphs = doc.getParagraphs();
//			List<DocxTextContent> textContents = new ArrayList<>();
//			
//			for (XWPFParagraph paragraph : paragraphs) {
//                String text = paragraph.getText();
//                boolean bold = paragraph.getRuns().stream().anyMatch(XWPFRun::isBold);
//                boolean italic = paragraph.getRuns().stream().anyMatch(XWPFRun::isItalic);
//                boolean underline = paragraph.getRuns().stream().anyMatch(XWPFRun::isStrike);
//
//                DocxTextContent textContent = new DocxTextContent(text, bold, italic, underline);
//                textContents.add(textContent);
//            }
//			for(XWPFPictureData pictureData : doc.getAllPictures()) {
//				String contentType = pictureData.getPackagePart().getContentType();
//				String imageBase64 = convertImageToBase64(pictureData.getData(), contentType);
//				docxContent.addImage(imageBase64);
//			}
//			docxContent.setTextContent(textContents);
//			
//		} catch (IOException e) {
//			// TODO: handle exception
//		}
//		
//		return docxContent;
//	}
//
//	private String replaceImageURLsWithBase64(String htmlContent, Metadata metadata) {
//        // Retrieve image URLs from the metadata
//        String[] imageUrls = metadata.getValues("resourceName");
//
//        // Replace image URLs with Base64 encoded images
//        for (String imageUrl : imageUrls) {
//            byte[] imageBytes = metadata.get(imageUrl).getBytes();
//            String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
//            htmlContent = htmlContent.replace(imageUrl, base64Image);
//        }
//
//        return htmlContent;
//    }
	

}
