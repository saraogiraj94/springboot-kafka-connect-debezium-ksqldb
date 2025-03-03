package com.mycompany.researchservice.rest;

import com.mycompany.researchservice.mapper.ResearcherMapper;
import com.mycompany.researchservice.model.Researcher;
import com.mycompany.researchservice.rest.dto.CreateResearcherRequest;
import com.mycompany.researchservice.rest.dto.ResearcherResponse;
import com.mycompany.researchservice.rest.dto.UpdateResearcherRequest;
import com.mycompany.researchservice.service.ResearcherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/researchers")
public class ResearcherController {

    private final ResearcherService researcherService;
    private final ResearcherMapper researcherMapper;

    @GetMapping
    public List<ResearcherResponse> getAllResearchers() {
        return researcherService.getAllResearchers()
                .stream()
                .map(researcherMapper::toResearcherResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResearcherResponse getResearcher(@PathVariable Long id) {
        Researcher researcher = researcherService.validateAndGetResearcher(id);
        return researcherMapper.toResearcherResponse(researcher);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResearcherResponse createResearcher(@Valid @RequestBody CreateResearcherRequest createResearcherRequest) {
        Researcher researcher = researcherMapper.toResearcher(createResearcherRequest);
        researcher = researcherService.saveResearchers(researcher);
        return researcherMapper.toResearcherResponse(researcher);
    }

    @PatchMapping("/{id}")
    public ResearcherResponse updateResearcher(@PathVariable Long id, @Valid @RequestBody UpdateResearcherRequest updateResearcherRequest) {
        Researcher researcher = researcherService.validateAndGetResearcher(id);
        researcherMapper.updateResearcherFromRequest(updateResearcherRequest, researcher);
        researcher = researcherService.saveResearchers(researcher);
        return researcherMapper.toResearcherResponse(researcher);
    }

    @DeleteMapping("/{id}")
    public ResearcherResponse deleteResearcher(@PathVariable Long id) {
        Researcher researcher = researcherService.validateAndGetResearcher(id);
        researcherService.deleteResearcher(researcher);
        return researcherMapper.toResearcherResponse(researcher);
    }
}
