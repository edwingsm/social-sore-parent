package ie.newship.social.score.service.controller;

import ie.newship.social.score.service.service.SocialScoreService;
import ie.newship.social.score.core.SearchKey;
import ie.newship.social.score.core.SocialScore;
import ie.newship.social.score.core.SocialScoreUpdate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SocialScoreController {

    @Autowired
    private SocialScoreService socialScoreService;

    @InitBinder ("key")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(SearchKey.class, new SearchKeyEnumEditor());
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody SocialScore socialScore) {
        SocialScore score = socialScoreService.save(socialScore);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> search(@RequestParam(value = "key", required = true) final SearchKey key,
                                    @RequestParam(value = "value", required = true) final String value) {
        System.out.println(key + "-" + value);
        //SearchKey keyType = SearchKey.valueOf(key.toUpperCase());
        List<SocialScore> socialScores =socialScoreService.searchSocialScores(key,value);
        return new ResponseEntity<>(socialScores,HttpStatus.OK);
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateSocialScoreById(@RequestBody SocialScoreUpdate request) {
        socialScoreService.updateSocialScore(request.getKeyName(),request.getKeyValue(), request.getUpdatedScore());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteSocialScoreById(@RequestParam(value = "key", required = true) final SearchKey key,
                                                   @RequestParam(value = "value", required = true) final String value) {
     //   SearchKey keyType = SearchKey.valueOf(key.toUpperCase());
        socialScoreService.deleteSocialScore(value, key);
        return ResponseEntity.ok().build();
    }

    @GetMapping("report")
    public ResponseEntity<?> getReport() {
        return ResponseEntity.ok(socialScoreService.generateReport());
    }

}
