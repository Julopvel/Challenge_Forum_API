package forum.api.controller;

import forum.api.domain.topic.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private TopicRepository topicRepository;

    @Autowired
    public TopicController(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    @PostMapping
    public ResponseEntity<TopicListData> registerTopic(@RequestBody @Valid TopicRegisterData topicRegisterData,
                                                       UriComponentsBuilder uriComponentsBuilder){
        Topic topic = topicRepository.save(new Topic(topicRegisterData));

        TopicListData topicListData = new TopicListData(topic.getId(),  topic.getTitle(), topic.getMessage(),
                topic.getCreationDate(), topic.getTopicStatus(), topic.getAuthor(),
                topic.getCourse());

        URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(uri).body(topicListData);
    }

    //Lists all the topics
    @GetMapping
    public ResponseEntity<Page<TopicListData>> listTopics(Pageable pageable){
        var topicId = topicRepository.findByActiveTrue(pageable);
        return ResponseEntity.ok(topicId.map(TopicListData::new));
    }

    //Shows the information of one topic, depending on the input id
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicListData> topicData(@PathVariable Long id){
        Topic topic =  topicRepository.getReferenceById(id);
        var topicInfo = new TopicListData(topic.getId(), topic.getTitle(), topic.getMessage(),
                topic.getCreationDate(), topic.getTopicStatus(), topic.getAuthor(),
                topic.getCourse());
        return ResponseEntity.ok(topicInfo);
    }

    //The method updates the topic info depending on the input id and returns a ResponseEntity,
    //showing the updated info
    @PutMapping
    @Transactional
    public ResponseEntity<TopicListData> updateTopic(@RequestBody @Valid TopicUpdateData topicUpdateData){
        Topic topic = topicRepository.getReferenceById(topicUpdateData.id());
        topic.updateData(topicUpdateData);
        return ResponseEntity.ok(new TopicListData(topic.getId(), topic.getTitle(), topic.getMessage(),
                topic.getCreationDate(), topic.getTopicStatus(), topic.getAuthor(), topic.getCourse()));
    }

    //The method disables the topic from the DB depending on the input id, doesn't erase the info though
    //if doesn't find anything, it will raise a 204 (no content)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Topic> deteletTopic(@PathVariable Long id){
        Topic topic = topicRepository.getReferenceById(id);
        topic.disableTopic();
        return ResponseEntity.noContent().build();
    }



}
