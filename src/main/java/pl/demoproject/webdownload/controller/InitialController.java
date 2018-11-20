package pl.demoproject.webdownload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import pl.demoproject.webdownload.Repository.WebRepository;
import pl.demoproject.webdownload.downloader.WebDownloader;
import pl.demoproject.webdownload.model.Website;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAsync
public class InitialController {

    @Autowired
    private WebRepository webRepository;

    @Autowired
    private WebDownloader webDownloader;

    @GetMapping("/websites/{id}")
    public Website getOneWebsite(@PathVariable(value = "id") Long webId ) {

        return webRepository.findById(webId).get();
    }

    @GetMapping("/websites")
    public List<Website> getSitesBySequence(@RequestParam(name = "sequence") Optional<String> sequence) {

        if (sequence.isPresent() == false){
            return webRepository.findAll();
        } else {
            return webRepository.findAllByAddressContaining(sequence.get());
        }
    }

    @PostMapping("/websites")
    public Long saveWebsite(@Valid @RequestBody Website website ) throws IOException, SQLException {

        Website itemInDb = webRepository.save(website);
        webDownloader.downloadAndSavePage(website.getAddress(), website.getId());
        return itemInDb.getId();
    }

    @DeleteMapping("/websites/{id}")
    public ResponseEntity<?> deleteWebsite(@PathVariable(value = "id") Long webId) {

        webRepository.delete(webRepository.getOne(webId));
        return ResponseEntity.ok().build();
    }
}
