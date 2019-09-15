package com.example.demo.controller;

import com.example.demo.modal.Course;
import com.example.demo.modal.dto.CourseDto;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


// single function interface
@RestController
@RequestMapping
public class CourseController {

    @Autowired // IOC 控制反转
    CourseService courseService; // Singleton
    //依赖注入 Dependency Injection

    @GetMapping(path = "/", produces = "application/json") //如果你用get方法 call我这个地址
    public HttpEntity findAllCourses(){
        List<Course> allCourses = courseService.findAllCourses(); //我就帮你用这个方法来处理这个请求

        return new ResponseEntity<>(allCourses,HttpStatus.OK); // 我返回结果给你
    }

//    @GetMapping(path = "/api/course/findAllCourses", produces = "application/json")
//    public HttpEntity<List<CourseDto>> findAllCourses(){
//        List<CourseDto> allCourses = courseService.findAllCourses();
//
//        return new ResponseEntity<>(allCourses, HttpStatus.OK);
//    }

    @GetMapping(path = "/look-up/{inputString}", produces = "application/json")
    public HttpEntity<Course> searchCourse(@PathVariable("inputString") String inputString) {

        List<Course> findedCourse = courseService.searchByCourseName(inputString);

        return new ResponseEntity(findedCourse, HttpStatus.OK);
    }

    @PostMapping("/two-sum")
    public ResponseEntity twoSum(
            @RequestBody @RequestParam("target") Integer target,
            @RequestBody @RequestParam("numbers") String nums) {
        try {
            List<String> numbers = new ArrayList<>(Arrays.asList(nums.split(",")));
            HashMap<Integer, Integer> map = new HashMap<>();
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < numbers.size(); i++) {
                int num = Integer.valueOf(numbers.get(i));
                if (map.get(num) != null) {
                    result.add(map.get(num));
                    result.add(i);
                }
                map.put(target - num, i);
            }

            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch(java.lang.NumberFormatException e) {
            return new ResponseEntity("bad parameter", HttpStatus.BAD_REQUEST);
        }
    }

}

// 增加一个课程/删除一个课程/更新一个课程信息
