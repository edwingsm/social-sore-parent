package ie.newswhip.social.score.service.controller;

import ie.newswhip.social.score.core.api.SocialScoreApi;
import ie.newswhip.social.score.core.api.SocialScoreSearchApi;
import ie.newswhip.social.score.core.model.SearchKey;
import ie.newswhip.social.score.core.model.SocialScore;
import ie.newswhip.social.score.core.model.SocialScoreUpdate;
import ie.newswhip.social.score.service.service.SocialScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class SocialScoreController implements SocialScoreApi<ResponseEntity>, SocialScoreSearchApi<ResponseEntity> {

    @Autowired
    private SocialScoreService socialScoreService;

    @InitBinder("key")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(SearchKey.class, new SearchKeyEnumEditor());
    }

    @Override
    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody SocialScore socialScore) {
        log.debug("Save Request {}", socialScore);
        SocialScore score = socialScoreService.save(socialScore);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @Override
    @GetMapping("search")
    public ResponseEntity<?> search(@RequestParam(value = "key", required = true) final SearchKey key,
                                    @RequestParam(value = "value", required = true) final String value) {
        log.debug("Search Request key={} ,value=", key, value);
        List<SocialScore> socialScores = socialScoreService.searchSocialScores(key, value);
        return new ResponseEntity<>(socialScores, HttpStatus.OK);
    }

    @Override
    @PatchMapping("update")
    public ResponseEntity update(@RequestBody SocialScoreUpdate socialScoreUpdate) {
        log.debug("Update Request {}", socialScoreUpdate);
        socialScoreService.updateSocialScore(socialScoreUpdate.getKey(), socialScoreUpdate.getValue(), socialScoreUpdate.getUpdatedScore());
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("delete")
    public ResponseEntity delete(SearchKey key, String value) {
        log.debug("Delete Request key={} ,value=", key, value);
        socialScoreService.deleteSocialScore(value, key);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("report")
    public ResponseEntity report() {
        log.debug("Get Report");
        return ResponseEntity.ok(socialScoreService.generateReport());
    }

}
