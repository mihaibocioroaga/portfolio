SELECT custname FROM public.cs130_2017_lab3 --Q1
WHERE custname LIKE '% M______'; 

SELECT booktitle, bookpages FROM public.cs130_2017_lab3 --Q2
WHERE bookpages % 2 = 0 AND 100 <= bookpages AND bookpages <= 200;

SELECT bookisbn FROM public.cs130_2017_lab3 --Q3
WHERE bookisbn SIMILAR TO '(0|7)%(0|7)';

SELECT booktitle FROM public.cs130_2017_lab3 --Q4
WHERE booktitle SIMILAR TO '%[0-9]%';

SELECT booktitle FROM public.cs130_2017_lab3 --Q5
WHERE booktitle SIMILAR TO '%((O|o)perative)%(web-enabled)%';

SELECT custiban FROM public.cs130_2017_lab3 --Q6
WHERE LENGTH(custiban) >= 26 AND 
custiban SIMILAR TO '((IE)|(CH)|(ES))%';

SELECT custiban FROM public.cs130_2017_lab3 --Q7
WHERE custiban SIMILAR TO '% \d{3}';

SELECT custiban FROM public.cs130_2017_lab3 --Q8
WHERE custiban SIMILAR TO '%\d{4} \d{4} \d{4}%';

SELECT custiban FROM public.cs130_2017_lab3 --Q9
WHERE custiban SIMILAR TO '%\s\d{4}(\s|$){6}%';

SELECT custregion, bookprice, bookpages FROM public.cs130_2017_lab3 --Q10
WHERE custregion SIMILAR TO '(IE)|(UK)' AND
bookprice + bookprice/100*12 > 60 AND
bookpages >= 100;

SELECT booktitle, booktext FROM public.cs130_2017_lab3 --Q11
WHERE booktext SIMILAR TO '(%CS130%){2,}';

SELECT booktitle, bookpages FROM public.cs130_2017_lab3 --Q12
WHERE LOG(bookpages) >= 2.2227 AND LOG(bookpages) <= 2.285555;