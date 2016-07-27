package org.literacyapp.web;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.literacyapp.dao.AudioDao;
import org.literacyapp.model.content.multimedia.Audio;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/audio")
public class AudioController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AudioDao audioDao;
    
    @RequestMapping(value="/{audioId}.{audioFormat}", method = RequestMethod.GET)
    public void handleRequest(
            Model model,
            @PathVariable Long audioId,
            @PathVariable String audioFormat,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("audioId: " + audioId);
        logger.info("audioFormat: " + audioFormat);
        
        Audio audio = audioDao.read(audioId);
        
        response.setContentType(audio.getContentType());
        response.setContentLength(audio.getBytes().length);
        
        byte[] bytes = audio.getBytes();
        try {
            outputStream.write(bytes);
        } catch (EOFException ex) {
            // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
            logger.warn(ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } finally {
            try {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (EOFException ex) {
                    // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
                    logger.warn(ex);
                }
            } catch (IOException ex) {
                logger.error(null, ex);
            }
        }
    }
}
