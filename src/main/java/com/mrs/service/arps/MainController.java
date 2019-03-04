package com.mrs.service.arps;

import com.sun.deploy.net.HttpResponse;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private static final String UPLOAD_DIR = "/uploads";

    @RequestMapping("/")
    String main(Model model, HttpServletRequest request) throws IOException, ServletException {
        File[] fList = new File(request.getServletContext().getRealPath("")+UPLOAD_DIR).listFiles();
        model.addAttribute("files", fList);
        return "index";
    }

    @RequestMapping("/upload")
    String upload(Model model, HttpServletRequest request) throws IOException, ServletException {

        String uploadFilePath = request.getServletContext().getRealPath("")+UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {fileSaveDir.mkdirs();}

        String fileName = null;
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
        }
        model.addAttribute("title"," File uploaded!");
        model.addAttribute("message",fileName);
        return "response";
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens)
            if (token.trim().startsWith("filename"))
                return token.substring(token.indexOf("=") + 2, token.length()-1);
        return "";
    }

    @RequestMapping("/arps2")
    String arps2(Model model, HttpServletRequest request) throws IOException, ServletException {
        String file =request.getParameter("file");
        ARPS    arps=new ARPS(file);
        model.addAttribute("title", arps.title);
        model.addAttribute("name", arps.name);
        model.addAttribute("arps20", arps.arps20);
        model.addAttribute("arps50", arps.arps50);
        return "arps2";
    }

    @RequestMapping("/arps3")
    String arps3(Model model, HttpServletRequest request) throws IOException, ServletException {
        String file =request.getParameter("file");
        ARPS arps=new ARPS(file);
        model.addAttribute("title", arps.title);
        model.addAttribute("name", arps.name);
        model.addAttribute("arps30", arps.arps30);
        return "arps3";
    }

}
