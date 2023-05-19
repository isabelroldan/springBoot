const gulp = require('gulp');
const sass = require('gulp-sass')(require('sass'));
const autoprefixer = require('gulp-autoprefixer');
const cleanCSS = require('gulp-clean-css');
const rename = require('gulp-rename');
const { exec } = require('child_process');

const sassPaths = 'src/main/resources/static/scss/*.scss';

gulp.task('sass', function () {
    return gulp
        .src(sassPaths)
        .pipe(sass().on('error', sass.logError))
        .pipe(
            autoprefixer({
                overrideBrowserslist: ['last 2 versions'],
                cascade: false,
            })
        )
        .pipe(gulp.dest('src/main/resources/static/css'))
        .pipe(cleanCSS())
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('src/main/resources/static/css'));
});

gulp.task('watch', function () {
    gulp.watch(sassPaths, gulp.series('sass'));
});

gulp.task('run', function (cb) {
    exec('mvnw.cmd spring-boot:run', function (err, stdout, stderr) {
        console.log(stdout);
        console.error(stderr);
        cb(err);
    });
});

gulp.task('default', gulp.parallel('sass', 'watch', 'run'));