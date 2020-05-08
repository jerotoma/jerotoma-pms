import { Component, Input } from '@angular/core';

import { NewsPost } from '../../../users/news.service';

@Component({
  selector: 'app-news-post',
  templateUrl: 'news-post.component.html',
})
export class NewsPostComponent {

  @Input() post: NewsPost;
}
