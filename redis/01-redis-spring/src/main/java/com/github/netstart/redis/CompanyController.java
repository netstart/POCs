package com.github.netstart.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<Company> findAll() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company findByIdentifier(@PathVariable("id") final Long id) {
        return companyService.findbyId(id);
    }

    @PostMapping
    public Company create(@RequestBody final Company company) {
        return companyService.create(company);
    }

    @PutMapping
    public Company update(@RequestBody final Company company) {
        return companyService.update(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id) {
        companyService.delete(id);
    }

}
