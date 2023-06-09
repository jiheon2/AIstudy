package kopo.poly.service.impl;

import kopo.poly.dto.StudentDTO;
import kopo.poly.persistance.mapper.IStudentMapper;
import kopo.poly.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService implements IStudentService {
    private final IStudentMapper studentMapper; // 오라클 DB와 연결된 Mapper

    @Override
    public List<StudentDTO> insertStudent(StudentDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertStudent Start!");

        // Student 테이블에 등록된 학생 아이디가 존재하는지 체크하기 위해 DB조회하기
        Optional<StudentDTO> res = Optional.ofNullable( // Optional.ofNullable : 결과가 null인지 구분할 때 사용
                studentMapper.getStudent(pDTO) // SELECT쿼리
        );
        // Optional이 static 메모리에 올라감

        if(!res.isPresent()) { // DB 조회 결과로 회원아이디가 없을 경우 / isPresent() : 값의 존재여부 결과를 반환
            studentMapper.insertStudent(pDTO); // 학생 등록 SQL 실행 / INSERT 쿼리
        }

        // 학생 테이블 전체 조회하기
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList() // SELECT 쿼리
        ).orElseGet(ArrayList::new);
        /* Optional.ofNullable().orElseGet() : 함수실행결과가 Null이라면, Null대신 값이 없음(Empty)로 처리
        Null : 메모리에 존재X, 메모리에 없는 데이터를 참조하면 NullPointerException 오류 발생
        Empty : 메모리에 변수 존재O 그러나 값이 없는 상태
         */

        log.info(this.getClass().getName() + ".insertStudent End!");

        return rList;
    }
}
