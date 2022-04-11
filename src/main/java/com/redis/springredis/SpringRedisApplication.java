package com.redis.springredis;

import com.redis.springredis.entity.Product;
import com.redis.springredis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@RestController
public class SpringRedisApplication {

    private final ProductDao dao;

    @Autowired
    public SpringRedisApplication(ProductDao dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Product product) {
        System.out.println("Product from UI = " + product);
        dao.save(product);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product_info");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllProducts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("all_products");
        modelAndView.addObject("products", dao.findAll());
        return modelAndView;
    }

    @GetMapping("/all/{id}")
    public ModelAndView findProduct(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product");
        modelAndView.addObject("product", dao.findProductById(id));
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public RedirectView remove(@PathVariable int id) {
          dao.deleteProduct(id);
          return new RedirectView("/all");
    }

    @GetMapping()
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
