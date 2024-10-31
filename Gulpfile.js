const {src, dest} = require('gulp');
const sass = require('sass');
const gulpSass = require('gulp-sass')(sass);

exports.buildSass = function () {
    console.log('Starting SASS compilation...');

    return src('gulp/scss/**/*.scss')
        .pipe(gulpSass().on('error', function (err) {
            console.error('SASS compilation error:', err.message);
            this.emit('end'); // 에러 발생 후 작업 종료
        }))
        .pipe(dest('src/main/resources/static/scss/')) // CSS 파일을 저장할 경로
        .on('end', function () {
            console.log('SASS compilation finished.');
        });
};
