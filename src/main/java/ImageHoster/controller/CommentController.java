package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import ImageHoster.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@RequestParam("comment") String text, @PathVariable Integer imageId,
                             Comment newComment, HttpSession session, Model model, @PathVariable String imageTitle) {
        newComment.setText(text);
        newComment.setCreatedDate(new Date());

        Image image= imageService.getImage(imageId);
        newComment.setImage(image);

        User user = (User) session.getAttribute("loggeduser");
        newComment.setUser(user);

        commentService.createComment(newComment);

        Image imageFetched = imageService.getImageByImageId(imageId);
        model.addAttribute("image", imageFetched);
        model.addAttribute("tags", imageFetched.getTags());
        model.addAttribute("comments", imageFetched.getComments());
        return "images/image";
    }


}
