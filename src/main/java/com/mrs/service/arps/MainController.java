package com.mrs.service.arps;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;

@Controller
public class MainController {

    private static final String UPLOAD_DIR = "uploads";

    @RequestMapping("/")
    String main(Model model){
        return "index";
    }

    @RequestMapping("/upload")
    String upload(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {fileSaveDir.mkdirs();}
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());

        String fileName = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
        }
//        request.setAttribute("message", fileName + " File uploaded successfully!");
//        getServletContext().getRequestDispatcher("/response.jsp").forward(request, response);
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

    @RequestMapping("/arps3")
    String arps3(Model model){
        ARPS    arps=new ARPS();
        arps.arpsLoad("02-01-03.arp");
//        model.addAttribute("arps3", arps.arps3);
        model.addAttribute("title", arps.title);
        model.addAttribute("name", arps.name);
        model.addAttribute("arps20", arps.arps20);
        model.addAttribute("arps50", arps.arps50);
//        model.addAttribute("arps30", arps.arps30);
        return "arps2";

    }

    @RequestMapping("/arps1")
    String arps1(Model model){
        ARPS    arps=new ARPS();
        arps.arpsLoad("02-01-01.arp");
//        model.addAttribute("arps3", arps.arps3);
        model.addAttribute("title", arps.title);
        model.addAttribute("name", arps.name);
        model.addAttribute("arps20", arps.arps20);
//        model.addAttribute("arps30", arps.arps30);
        model.addAttribute("arps50", arps.arps50);
        return "arps2";
    }

    @RequestMapping("/arps2")
    String arps2(Model model){
        ARPS    arps=new ARPS();
        arps.arpsLoad("02-01-02.arp");
//        model.addAttribute("arps3", arps.arps3);
        model.addAttribute("title", arps.title);
        model.addAttribute("name", arps.name);
        model.addAttribute("arps20", arps.arps20);
//        model.addAttribute("arps30", arps.arps30);
        model.addAttribute("arps50", arps.arps50);
        return "arps2";
    }

}
