import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FileDetails } from '../file-details.model';
import { FileUploadService } from '../services/file-upload.service';

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrls: ['./upload-file.component.css']
})
export class UploadFileComponent implements OnInit {

  file!: File;
  fileDetails!: FileDetails;

  constructor(private fileUploadService: FileUploadService, private router: Router) { }

  ngOnInit(): void {
  }

  selectFile(event: any) {
    this.file = event.target.files.item(0);
  }

  uploadFile() {
    this.fileUploadService.upload(this.file).subscribe({
      next: (data) => {
        this.fileDetails = data;
        this.redirectToUploadedFile();
      },
      error: (e) => {
        console.log(e);
      }
    });
  }

  redirectToUploadedFile() {
    window.open(this.fileDetails.fileUri, '_blank');
  }

}
