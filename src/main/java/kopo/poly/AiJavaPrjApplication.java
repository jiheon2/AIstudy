package kopo.poly;

import kopo.poly.dto.NlpDTO;
import kopo.poly.dto.OcrDTO;
import kopo.poly.service.INlpService;
import kopo.poly.service.IOcrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@Slf4j
@RequiredArgsConstructor // Spring 메모리에 올라온 객체를 변수에 넣어주는 기능
@SpringBootApplication
public class AiJavaPrjApplication implements CommandLineRunner {
    // CommandLIneRunner 를 사용하지 않으면 Service 영역에 정의된 객체 사용 불가

    private final IOcrService ocrService; // 이미지 인식
    // @Service 정의된 자바파일
    // Spring framework가 실행될 때, @Service로 정의한 자바는 자동으로 메모리에 올라감
    // 메모리에 올라간 OcrService 객체를 ocrService 변수에 객체를 넣어준다
    private final INlpService nlpService; // 자연어 처리
    // 내가 만든 Service를 가져와서 사용하기 위해 private final을 사용?
    // 실행될때 Service가 자동으로 올라감 > 올라간 것이 INlpService와 일치하면 nlpService에 가져와서 사용?

    public static void main(String[] args) {
        SpringApplication.run(AiJavaPrjApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("자바 프로그래밍 시작!");

        String filePath = "Image";
        String fileName = "sample02.jpg";

        OcrDTO pDTO = new OcrDTO();

        pDTO.setFilePath(filePath);
        pDTO.setFileName(fileName);

        OcrDTO ocrDTO = ocrService.getReadforImageText(pDTO);

        String result = ocrDTO.getResult();

        log.info("인식된 문자열");
        log.info(result);

        log.info("------------------------------------------------------");

        NlpDTO nlpDTO = nlpService.getPlainText(result); // 품사 분석 결과를 가져옴
        log.info("형태소별 품사 분석 결과 : " + nlpDTO.getResult());

        nlpDTO = nlpService.getNouns(result);
        // 품사 분석결과에서 명사만 추출한 결과를 저장함

        List<String> nouns = nlpDTO.getNouns();
        // 명사만 추출한 결과를 LIst 형태의 nouns 변수에 저장

        Set<String> distinct = new HashSet<>(nouns);
        // 중복제거
        // Set은 중복을 허용하지 않음 / List를 Set구조로 변환함

        log.info("중복제거 수행 전 단어 수 : " + nouns.size());
        log.info("중복제거 수행 후 단어 수 : " + distinct.size());

        Map<String, Integer> rMap = new HashMap<>();
        // 단어, 빈도수를 Map 구조로 저장하기 위해 객체 생성
        // Map 구조의 키는 중복 불가능(값은 중복가능)
        // <String, Integer>의 구조

        for (String s : distinct) { // distinct의 각 요소를 s에 순차적으로 가져옴(a1, a2, a3...)
            int count = Collections.frequency(nouns, s); // nouns에서 s가 등장하는 횟수를 count에 저장하는 코드
            // frequency 메서드 : 주어진 컬렉션에서 특정 요소가 등장하는 횟수를 반환하는 정적 메서드
            rMap.put(s, count); // s 는 키, count를 값으로 가지는 엔트리를 추가하는 코드

            log.info(s + " : " + count);
        }

        List<Map.Entry<String, Integer>> sortResult = new LinkedList<>(rMap.entrySet());
        // 빈도수 결과를 정렬하는 코드
        // 정렬을 위해 맵에 저장된 레코드 1개(키, 값) 즉 맵의 형태를 리스트 구조로 변경하는 코드
        // rMap.entrySet() : rMap의 모든 엔트리를 Set형태로 반환
        // 엔트리 : 키 / 값의 쌍을 나타내는 개체
        // LinkedList는 List인터페이스의 구현체
        // sortResult는 rMap의 엔트리를 가지고 있는 LinkedList
        // LinkedList는 순서를 바꾸거나 정렬할 수 있음
        // rMap이라는 Map<String, Integer>객체의 엔트리들을 리스트로 변환하고 sortResult라는 변수에 할당하는 코드

        Collections.sort(sortResult, ((o1, o2) -> o2.getValue().compareTo(o1.getValue())));
        // 람다식이 적용되었고 저장된 list 결과를 정렬하는 코드
        // o1, o2 앞의 값, 뒤의 값 > o2(뒤의 값)을 o1(앞의 값)과 비교하면서 정렬하는 코드

        log.info("가장 많이 사용된 단어는? : " + sortResult);

        log.info("자바 프로그램 종료!");
    }
    // Override영역에 실제 코드를 작성해야함
    // 스프링은 인터페이스를 통해 자바코드를 본다
    // 보내는(전달하는) 변수명 pDTO(파라미터), 받는 변수명 rDTO(result)로 개발자들이 많이 씀
}
