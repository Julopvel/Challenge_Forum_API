package forum.api.controller;

import forum.api.domain.topic.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private TopicRepository topicRepository;

    @Autowired
    public TopicController(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    //To register a new topic
    @PostMapping
    public void registerTopic(@RequestBody @Valid TopicRegisterData topicRegisterData){
        topicRepository.save(new Topic(topicRegisterData));
    }

    //Lists all the topics
    @GetMapping
    public Page<Topic> listTopics(Pageable pageable){
        return topicRepository.findAll(pageable);
    }

    //Shows the information of one topic, depending on the input id
    @GetMapping("/{id}")
    @Transactional
    public TopicData topicData(@PathVariable Long id){
        Topic topic =  topicRepository.getReferenceById(id);
        var topicInfo = new TopicData(topic.getId(), topic.getTitle(), topic.getMessage(),
                topic.getCreationDate(), topic.getTopicStatus(), topic.getAuthor(),
                topic.getCourse());
        return topicInfo;
    }

    //The method updates the topic info depending on the input id
    @PutMapping
    @Transactional
    public void updateTopic(@RequestBody @Valid TopicUpdateData topicUpdateData){
        Topic topic = topicRepository.getReferenceById(topicUpdateData.id());
        topic.updateData(topicUpdateData);
    }

    //The method deletes completely the topic from the DB depending on the input id
    @DeleteMapping("/{id}")
    @Transactional
    public void deteletTopic(@PathVariable Long id){
        Topic topic = topicRepository.getReferenceById(id);
        topicRepository.delete(topic);
    }



}
