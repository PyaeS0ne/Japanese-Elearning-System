package jels.JapaneseELearningSystem.entity;

import lombok.Data;
import lombok.ToString;

@Data 
@ToString
public class ChangeName {
	String new_name;
	int ori_id;
}
