package com.sinor.main.Interest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinor.main.Interest.model.Interest;

@Service
public class InterestService {
    /*
        실제 반환값에 대한 테스트 로직 -> 이후 JPA에 더미데이터를 넣어서 수정 예정임.
        2023.12.05
        이상민
     */

	//    public List<Interest> getAllInterests() {
	//        // 비즈니스 로직: 데이터베이스에서 관심사 목록을 가져오는 코드 (가정)
	//        return Arrays.asList(
	//                new Interest(1, "여행", "여행", "2023-08-01T00:38:47.000Z", "2023-08-01T00:38:47.000Z", null),
	//                new Interest(2, "여행", "낚시", "2023-08-01T00:38:47.000Z", "2023-08-01T00:38:47.000Z", null)
	//        );
	//    }
	private final InterestRepository interestRepository;

	@Autowired
	public InterestService(InterestRepository interestRepository) {
		this.interestRepository = interestRepository;
	}

	public List<Interest> getInterests() {
		// Spring Data JPA의 findAll 메서드를 사용하여 모든 관심사를 가져옵니다.
		return interestRepository.findAll();
	}
}
