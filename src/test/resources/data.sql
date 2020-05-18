INSERT INTO public.typo
(id, page_url, reporter_name, reporter_remark, text_after_typo, text_before_typo, text_typo, typo_status)
 VALUES
(1, 'https://site.com/article', 'User1', 'This''s an typo', ' which I reed', 'This''s the text of an ', 'article', 'REPORTED'),
(2, 'https://site.com/page', 'User2', 'This''s an typo', ' which I reed', 'This''s the text of an ', 'article', 'CANCELED'),
(3, 'https://site.com/clients', 'User3', 'This''s an typo', ' which I reed', 'This''s the text of an ', 'article', 'IN_PROGRESS'),
(4, 'https://site.com/example', 'User4', 'This''s an typo', ' which I reed', 'This''s the text of an ', 'article', 'RESOLVED'),
(5, 'https://site.com/', 'User5', 'This''s an typo', ' which I reed', 'This''s the text of an ', 'article', 'REPORTED');
