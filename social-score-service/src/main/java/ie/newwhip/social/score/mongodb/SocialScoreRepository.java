package ie.newwhip.social.score.mongodb;

import ie.newwhip.social.score.mongodb.dbo.SocialScoreDBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface SocialScoreRepository extends MongoRepository<SocialScoreDBO, String>, SocialScoreReportRepository {
    Page<SocialScoreDBO> findByDomainName(String domainName, Pageable pageable);

    Optional<SocialScoreDBO> findByUrl(String url);

}
