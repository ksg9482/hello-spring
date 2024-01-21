package hello.hellospring.domain.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); //insert작업을 단순하게 해줌.
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); //member 테이블에 id컬럼 값을 생성.

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName()); //parameters객체에 member입력

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); //insert실행하고 생성된 키 반환
        member.setId(key.longValue());//member에 반환된 키 입력

        return member; //member객체에 이름은 이미 입력으로 들어가 있음. 저장하고 키 값이 반환되면 제대로된 키 값을 넣어서 반환.
    }

    @Override
    public Optional<Member> findById(Long id) {
        //query, rowmapper, parameter. 쿼리 파라미터를 입력해서 쿼리 작성. 결과물은 로우매퍼로 보내서 가공.
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        //return new RowMapper<Member>() { 람다로 변환 가능
        //응답 결과를 받아 member 도메인 객체에 입력해서 리스트로 반환함.
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
