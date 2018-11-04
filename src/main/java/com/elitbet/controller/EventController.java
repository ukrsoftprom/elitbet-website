package com.elitbet.controller;

import com.elitbet.model.Event;
import com.elitbet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/events")
class EventController {
    @Autowired
    EventService eventService;

    @GetMapping
    @ResponseBody
    public Page<Event> findAllNotStarted(
            @RequestParam("page") int page,
            @RequestParam("size") int size){

        return eventService.findAllNotStarted(page,size);
    }

}