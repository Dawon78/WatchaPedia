package org.doit.watcha.collection;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [DTO] CollectionRequest
 * 화면(HTML)에서 전송한 컬렉션 생성 데이터를 담는 객체입니다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionRequest {

    // 1. 컬렉션 제목 (input name="collectionTitle")
    private String collectionTitle;

    // 2. 컬렉션 설명 (textarea name="collectionDesc")
    private String collectionDesc;

    /**
     * 3. 선택된 작품 ID 리스트 (input name="workIds")
     * * HTML에서 <input type="hidden" name="workIds" value="101"> 
     * 형태로 여러 개가 전송되면 스프링이 자동으로 이 List에 담아줍니다.
     */
    private List<Integer> workIds;

    /**
     * 💡 개발 팁:
     * Entity(Collection.java)는 실제 DB 테이블과 연결되어 복잡한 관계(@ManyToMany)를 가지지만,
     * Request DTO는 딱 화면에서 필요한 '데이터 그 자체'만 깔끔하게 들고 있도록 설계합니다.
     */
}