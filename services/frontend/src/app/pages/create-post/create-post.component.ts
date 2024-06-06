import { Component } from '@angular/core';
import { AddPostFormComponent } from '../../components/add-post-form/add-post-form.component';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [
    AddPostFormComponent
  ],
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss', '../../app.component.scss']
})
export class CreatePostComponent {

}
