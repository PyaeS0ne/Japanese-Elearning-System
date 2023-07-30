package jels.JapaneseELearningSystem.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChangePassword {
	String current_pwd;
	String new_pwd;
	String comfirm_new_pwd;
	int ori_id;

}
