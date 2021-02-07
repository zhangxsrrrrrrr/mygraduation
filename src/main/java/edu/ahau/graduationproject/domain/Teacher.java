package edu.ahau.graduationproject.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher implements Serializable {
    private static final long serialVersionUID = -6200048139618133408L;
    private String tchId;
    private String tchName;
    private String tchPassword;
    private String tchQQ;
    private String tchPhone;
}
