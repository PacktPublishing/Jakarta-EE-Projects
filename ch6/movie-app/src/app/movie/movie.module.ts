import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MovieRoutingModule } from './movie-routing.module';
import { MovieListComponent } from './movie-list/movie-list.component';


@NgModule({
  imports: [CommonModule, MovieRoutingModule],
  declarations: [MovieListComponent]
})
export class MovieModule {}
