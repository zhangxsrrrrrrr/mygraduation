package edu.ahau.graduationproject.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2021/2/18.
 *
 * @author Xun Zhang
 */
@Data
public class FileName implements Serializable {
    private static final long serialVersionUID = 2111581512652989942L;
    private String fileName;
}
