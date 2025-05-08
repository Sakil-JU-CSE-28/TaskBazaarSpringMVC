package controller;

import dto.BidDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.BidService;
import service.PostService;
import util.Constants;
import validation.BidValidator;


import java.util.List;

@Controller
@AllArgsConstructor
@PreAuthorize("hasRole('FREELANCER')")
@RequestMapping("/bids")
public class BidController {

    private final BidValidator bidValidator;
    private final BidService bidService;
    private final PostService postService;


    @GetMapping("/add/{postId}/{bidder}")
    public String add(@PathVariable Long postId, @PathVariable String bidder, RedirectAttributes redirectAttributes) {
        bidValidator.validate(postId, bidder);
        bidService.save(postId, bidder);
        redirectAttributes.addFlashAttribute("successMessage", "Bidder " + bidder + " was successfully added to post #" + postId);
        return "redirect:/posts";
    }

    @GetMapping("/getAll/{postId}")
    public String getAllByPostId(@PathVariable Long postId, Model model) {
        List<BidDto> bids = bidService.getAllByPostId(postId);
        model.addAttribute("bids", bids);
        return "list-bids";
    }

    @PostMapping("/{bidId}/{postId}/accept")
    public String accept(@PathVariable Long bidId, @PathVariable Long postId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!postService.isOwner(postId, authentication.getName())) throw new RuntimeException(Constants.Error.UNAUTHORIZED);
        bidService.accept(bidId);
        return "redirect:/posts";
    }
}
