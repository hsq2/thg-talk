package com.thg.accelerator.THGTalk.controller;

import com.thg.accelerator.THGTalk.event.SubTalkJSON;
import com.thg.accelerator.THGTalk.model.SubTalk;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/subtalk")
public class SubTalkController extends CRUDController<SubTalk, SubTalkJSON> {
}
