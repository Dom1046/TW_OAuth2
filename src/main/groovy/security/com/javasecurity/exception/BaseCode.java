package security.com.javasecurity.exception;

import security.com.javasecurity.dto.ReasonDto;

public interface BaseCode {
    public ReasonDto getReason();

    public ReasonDto getReasonHttpStatus();
}