package ie.newwhip.social.score.service.mongodb;

import com.mongodb.client.result.UpdateResult;
import ie.newwhip.social.score.service.mongodb.dbo.SocialScoreDBO;
import ie.newwhip.social.socre.client.score.core.SocialScoreReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class SocialScoreReportRepositoryImpl implements SocialScoreReportRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<SocialScoreReport> aggregateSocialScoreByDomainName() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group ("domainName").addToSet("domainName").as("domain")
                        .count().as("urlCount")
                        .sum("score").as("score"),
                Aggregation.sort(Sort.Direction.ASC, Aggregation.previousOperation(), "domainName"));
        AggregationResults<SocialScoreReport> groupResults = mongoTemplate.aggregate(
                aggregation, SocialScoreDBO.class, SocialScoreReport.class);
        List<SocialScoreReport> salesReport = groupResults.getMappedResults();
        return salesReport;
    }

    @Override
    public SocialScoreDBO updateSocialScore(String key,String value, long score) {
        Query searchUserQuery = new Query(Criteria.where(key).is(value));
        //SocialScoreDBO scoreDBO =mongoTemplate.findOne(searchUserQuery,SocialScoreDBO.class);
        UpdateResult updateResult =  mongoTemplate.updateFirst(searchUserQuery,
                Update.update("updatedScore",score), SocialScoreDBO.class);
        return mongoTemplate.findOne(searchUserQuery, SocialScoreDBO.class);
    }
}
