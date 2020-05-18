package fr.kacetal.typo.web;

public class WebConstants {

    static final String TYPO_TEMPLATE_DIR = "typo";

    static final String TYPO_LIST_PAGE = TYPO_TEMPLATE_DIR + "/list";

    static final String TYPO_VIEW_PAGE = TYPO_TEMPLATE_DIR + "/view";

    static final String TYPO_CREATE_PAGE = TYPO_TEMPLATE_DIR + "/create";

    static final String TYPO_UPDATE_PAGE = TYPO_TEMPLATE_DIR + "/update";

    static final String TYPO_ROOT = "/typos";

    static final String API_VERSION = "/api/v1";

    public static final String TYPO_API_ROOT = API_VERSION + TYPO_ROOT;

    static final String REDIRECT_TO_TYPO_ROOT = "redirect:" + TYPO_ROOT + "/";
}
