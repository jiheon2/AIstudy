package kopo.poly.service;

import kopo.poly.dto.NlpDTO;

public interface INlpService {
    NlpDTO getPlainText(String text);
    // 분석할 문장의 형태소별 품사 분석 결과 가져오기
    // text가 분석할 문장

    NlpDTO getNouns(String text);
    // 분석할 문장의 형태소 중 명사만 가져오기
    // 명사 결과는 여러개가 출력되기 때문에 1차원 배열 구조인 List에 저장
}
