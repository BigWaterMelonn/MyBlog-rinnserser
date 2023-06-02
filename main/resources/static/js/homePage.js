// 获取评论表单元素和评论容器元素
var commentForm = document.getElementById('comment-form');
var commentContainer = document.getElementById('comment-container');

// 监听表单提交事件
commentForm.addEventListener('submit', function (event) {
    event.preventDefault(); // 阻止表单默认提交行为

    // 获取姓名和评论内容
    var name = document.getElementById('name').value;
    var comment = document.getElementById('comment').value;

    // 创建评论项元素
    var commentItem = document.createElement('div');
    commentItem.classList.add('comment-item');

    // 创建姓名和评论内容元素，并设置其文本内容
    var nameElement = document.createElement('p');
    nameElement.classList.add('comment-name');
    nameElement.textContent = name;

    var commentElement = document.createElement('p');
    commentElement.classList.add('comment-content');
    commentElement.textContent = comment;

    // 创建删除按钮，并添加删除功能
    var deleteButton = document.createElement('button');
    deleteButton.classList.add('comment-delete');
    deleteButton.textContent = 'Delete';
    deleteButton.addEventListener('click', function () {
        commentItem.remove(); // 删除评论项
    });

    // 将姓名、评论内容和删除按钮添加到评论项元素中
    commentItem.appendChild(nameElement);
    commentItem.appendChild(commentElement);
    commentItem.appendChild(deleteButton);

    // 将评论项添加到评论容器中
    commentContainer.appendChild(commentItem);

    // 清空表单输入
    document.getElementById('name').value = '';
    document.getElementById('comment').value = '';
});
// 模拟从数据库获取的博客数据
var blogData = [
    { author: 'John Doe', title: 'Blog Post 1' },
    { author: 'Jane Smith', title: 'Blog Post 2' },
    { author: 'Bob Johnson', title: 'Blog Post 3' }
];

// 获取博客列表容器
var blogListContainer = document.getElementById('blog-list-container');

// 创建博客列表项并添加到容器中
blogData.forEach(function(blog) {
    var blogItem = createBlogItem(blog);
    blogListContainer.appendChild(blogItem);
});

function createBlogItem(blog) {
    // 创建博客列表项的容器元素
    var blogItem = document.createElement('div');
    blogItem.classList.add('blog-item');
  
    // 创建博客作者元素，并设置其文本内容
    var authorElement = document.createElement('p');
    authorElement.classList.add('blog-author');
    authorElement.textContent = 'Author: ' + blog.author;
  
    // 创建博客标题元素，并设置其文本内容
    var titleElement = document.createElement('p');
    titleElement.classList.add('blog-title');
    titleElement.textContent = 'Title: ' + blog.title;
  
    // 创建删除按钮元素，并添加点击事件，使其能够删除博客列表项
    var deleteButton = document.createElement('button');
    deleteButton.classList.add('blog-delete');
    deleteButton.textContent = 'Delete';
    deleteButton.addEventListener('click', function() {
      blogItem.remove(); // 删除博客列表项
    });
  
    // 将作者、标题和删除按钮添加到博客列表项容器中
    blogItem.appendChild(authorElement);
    blogItem.appendChild(titleElement);
    blogItem.appendChild(deleteButton);
  
    return blogItem;
  }
  


