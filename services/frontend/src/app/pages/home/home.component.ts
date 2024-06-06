import { Component, OnInit } from '@angular/core';
import { PostListComponent } from '../../components/post-list/post-list.component';
import { PostService } from '../../services/post.service';
import { ButtonActiveComponent } from '../../components/button-active/button-active.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    PostListComponent,
    ButtonActiveComponent,
    RouterLink
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss', '../../app.component.scss']
})
export class HomeComponent implements OnInit {
  posts: Array<any> = [];

  constructor(
    private postService: PostService
  ) {}

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe({
      next: (res) => {
        this.posts = [...res.posts];
      }
    })
  }
}
