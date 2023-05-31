package kopo.poly.service;

import kopo.poly.dto.OcrDTO;
public interface IOcrService {
    // 인터페이스는 메모리에 안올라감
    // 함수명만 제공, 실제 기능은 Service에 구현
    // Service가 메모리에 올라갈때 인터페이스가 같이 올라감
    // 스프링이 메모리에 한번 올리면 상수가 됨(메모리에 올라가있어서 값을 바꾸지 못함)
    // service(패키지)안에 인터페이스 작성 후 impl패키지에 실행할 기능(자바)을 만듬
    String modelFile = "c:/model/tessdata";

    OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception;
}
