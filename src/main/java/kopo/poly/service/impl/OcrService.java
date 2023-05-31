package kopo.poly.service.impl;

import kopo.poly.dto.OcrDTO;
import kopo.poly.service.IOcrService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j // 로그와 관련된 어노테이션
@Service
public class OcrService implements IOcrService { // 인터페이스(IOcrService)를 클래스(OcrService)로 구현해라
    @Override
    public OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getReadforImageText start!");
        // log.info를 통해서 시작과 끝에 로그를 찍어 함수들이 잘 실행되었는지를 확인하는 습관을 들이는 것이 좋다
        // this.getClass().getName() : 현재 클래스의 이름을 가져오는 메서드

        ClassPathResource resource = new ClassPathResource(pDTO.getFilePath() + "/" + pDTO.getFileName());
        // resources 폴더 밑에 존재하는 파일을 활용하기 위해서는 반드시 ClassPathResource 객체를 사용해야함

        ITesseract instance = new Tesseract();
        // OCR기술을 사용하기 위해 Tesseract 플랫폼 객체 생성(메모리에 올림)

        instance.setDatapath(IOcrService.modelFile);
        // modelFile = OCR분석에 필요한 기준데이터(다운받은것(이미 학습시켜 놓은 나라별 데이터))
        // 저장경로는 물리경로(전체경로(인터페이스에 선언해놓은 경로))

        instance.setLanguage("eng");
        // 한국어 학습데이터 선택(기본값은 영어)
        // 다른 나라 언어를 선택하려면 다른 것을 받아야함

        String result = instance.doOCR(resource.getFile());
        // 이미지 파일로 부터 텍스트 읽기

        pDTO.setResult(result);
        // 읽은 글자를 DTO에 저장하기

        log.info(this.getClass().getName() + ".getReadforImageTExt End!");

        return pDTO;
    }
}
