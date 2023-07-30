package jels.JapaneseELearningSystem.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
@Builder
public class DocxContent {
	
	private String textContent;
	private List<String> images;
	
	public DocxContent() {
        images = new ArrayList<>();
    }

	 public void addImage(String image) {
	        images.add(image);
	    }

}
