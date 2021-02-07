package edu.ahau.graduationproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created on 2021/2/4.
 *
 * @author Xun Zhang
 */
@Data
@ToString
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 2449912859494026539L;
    private String ID;
    private String password;
}
