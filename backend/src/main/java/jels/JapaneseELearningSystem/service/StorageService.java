package jels.JapaneseELearningSystem.service;

import java.nio.file.Path;

import java.util.stream.Stream;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import jels.JapaneseELearningSystem.entity.DocxContent;


@Configuration
public interface StorageService {
	
	
	public String saveFile(MultipartFile file);
	public DocxContent readDocxFile(String fileName);
	//public String readDocxFile(String fileName); 
	public String update(String oldFileName,MultipartFile file);
	//public List<String> saveMultipleFile(MultipartFile [] file);
	public byte[] load(String filename);
	public boolean delete(String fileName);
	
	public Stream<Path> loadAll();

}
