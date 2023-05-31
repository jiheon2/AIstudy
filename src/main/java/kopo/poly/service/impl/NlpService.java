package kopo.poly.service.impl;

import kopo.poly.dto.NlpDTO;
import kopo.poly.service.INlpService;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NlpService  implements INlpService {

    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // 학습용 모델의 데이터가 가장 큰것을 사용(FULL 학습량 많음 / LIGHT 학습량 적음)

    @Override
    public NlpDTO getPlainText(String text) {
        log.info(this.getClass().getName() + ".getPlainText Start!");

        KomoranResult komoranResult = komoran.analyze(text); // 인식된 문자열을 분석한 결과

        String result = komoranResult.getPlainText(); // 모든 단어에 품사를 알려줌

        NlpDTO rDTO = new NlpDTO();
        rDTO.setResult(result);

        log.info(this.getClass().getName() + ".getPlainText End!");

        return rDTO;
    }

    @Override
    public NlpDTO getNouns(String text) {

        log.info(this.getClass().getName() + ".getNouns Start!");

        KomoranResult komoranResult = komoran.analyze(text); // 인식된 문자열 분석 결과

        List<String> nouns = komoranResult.getNouns();  // 품사가 N으로 시작하는것을 가져옴 / List<string>으로 결과값을 제공 / nouns에 결과값을 넣어줌 Lombok의 @Setter를 통해서

        NlpDTO rDTO = new NlpDTO();
        rDTO.setNouns(nouns);

        log.info(this.getClass().getName() + ".getNouns End!");

        return rDTO;
    }
}
