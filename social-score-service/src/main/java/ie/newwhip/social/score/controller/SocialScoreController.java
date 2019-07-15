package ie.newwhip.social.score.controller;

import ie.newwhip.social.score.model.KeyName;
import ie.newwhip.social.score.model.SocialScoreUpdateRequest;
import ie.newwhip.social.score.model.SocialScore;
import ie.newwhip.social.score.service.SocialScoreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SocialScoreController {

    @Autowired
    private SocialScoreService socialScoreService;

    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody SocialScore socialScore) {
        SocialScore score = socialScoreService.save(socialScore);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<?> search(@RequestParam(value = "key", required = true) final String key,
                                    @RequestParam(value = "value", required = true) final String value) {
        System.out.println(key + "-" + value);
        KeyName keyType = KeyName.valueOf(key.toUpperCase());
        List<SocialScore> socialScores =socialScoreService.searchSocialScores(keyType,value);
        return new ResponseEntity<>(socialScores,HttpStatus.OK);
    }

    @PatchMapping("update")
    public ResponseEntity<?> updateSocialScoreById(@RequestBody SocialScoreUpdateRequest request) {
        socialScoreService.updateSocialScore(request.getKeyName(),request.getKeyValue(), request.getScore());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteSocialScoreById(@RequestParam(value = "key", required = true) final String key,
                                                   @RequestParam(value = "value", required = true) final String value) {
        KeyName keyType = KeyName.valueOf(key.toUpperCase());
        socialScoreService.deleteSocialScore(value, keyType);
        return ResponseEntity.ok().build();
    }

    @GetMapping("report")
    public ResponseEntity<?> getReport() {
        return ResponseEntity.ok(socialScoreService.generateReport());
    }

}
