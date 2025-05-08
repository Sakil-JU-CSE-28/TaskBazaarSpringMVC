package validation;


import dto.PostDto;
import exception.ValidationException;
import util.Constants;
import util.Utils;

import java.util.HashMap;
import java.util.Map;

public class PostValidator {


    public static void validate(PostDto postDto) {
        String title = postDto.getTitle();
        String content = postDto.getContent();
        Map<String,String> errors = new HashMap<>();
        if (Utils.isNullOrEmpty(title)) {
            errors.put("Title",Constants.Error.EMPTY_TITLE);
        }
        if (Utils.isNullOrEmpty(content)) {
            errors.put("Content",Constants.Error.EMPTY_CONTENT);
        }
        if (title.length() > Constants.Limit.MAX_TITLE_LENGTH) {
            errors.put("Title_len",Constants.Error.TITLE_LENGTH_ERROR);
        }
        if (content.length() > Constants.Limit.MAX_CONTENT_LENGTH) {
            errors.put("Content_len",Constants.Error.CONTENT_LENGTH_ERROR);
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors.toString(),errors);
        }
    }

}
