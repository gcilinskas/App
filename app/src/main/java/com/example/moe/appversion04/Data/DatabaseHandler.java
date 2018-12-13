package com.example.moe.appversion04.Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moe.appversion04.Data.DatabaseStructure.CategoryDB;
import com.example.moe.appversion04.Data.DatabaseStructure.FavoriteDB;
import com.example.moe.appversion04.Data.DatabaseStructure.JobPostDB;
import com.example.moe.appversion04.Data.DatabaseStructure.MessageDB;
import com.example.moe.appversion04.Data.DatabaseStructure.ReviewDB;
import com.example.moe.appversion04.Data.DatabaseStructure.SubcategoryDB;
import com.example.moe.appversion04.Data.DatabaseStructure.Tables;
import com.example.moe.appversion04.Data.DatabaseStructure.UserDB;
import com.example.moe.appversion04.Model.Category;
import com.example.moe.appversion04.Model.Favorite;
import com.example.moe.appversion04.Model.JobPost;
import com.example.moe.appversion04.Model.Message;
import com.example.moe.appversion04.Model.Review;
import com.example.moe.appversion04.Model.Subcategory;
import com.example.moe.appversion04.Model.User;
import com.example.moe.appversion04.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;
    private Tables tables = new Tables();
    private UserDB userDB = new UserDB(ctx);
    private JobPostDB jobPostDB = new JobPostDB(ctx);
    private CategoryDB categoryDB = new CategoryDB(ctx);
    private SubcategoryDB subcategoryDB = new SubcategoryDB(ctx);
    private FavoriteDB favoriteDB = new FavoriteDB(ctx);
    private ReviewDB reviewDB = new ReviewDB(ctx);
    private MessageDB messageDB = new MessageDB(ctx);
    private static DatabaseHandler db = null;

    public static DatabaseHandler getInstance(Context context) {
        if (db == null) {
            db = new DatabaseHandler(context);
        }
        return db;
    }

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     tables.createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        tables.deleteTables(db);
    }

    public void addUser(User user){
        UserDB userDB = new UserDB(ctx);
        userDB.addUser(user);
    }

    public User getUser(int id) {
        UserDB userDB = new UserDB(ctx);
        return userDB.getUser(id);
    }

    public List<User> getAllUsers(){
        UserDB userDB = new UserDB(ctx);
        return userDB.getAllUsers();
    }

    public int updateUser(User user) {
        UserDB userDB = new UserDB(ctx);
        return userDB.updateUser(user);
    }

    public void deleteUser(int id){
        UserDB userDB = new UserDB(ctx);
        userDB.deleteUser(id);
    }

    public int getUsersCount(){
        UserDB userDB = new UserDB(ctx);
        return userDB.getUsersCount();
    }


    public void addJobPost(JobPost jobPost) {
        JobPostDB jobPostDB = new JobPostDB(ctx);
        jobPostDB.addJobPost(jobPost);
    }

    public JobPost getJobPost(int id) {
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getJobPost(id);
    }

    public List<JobPost> getAllJobPosts(){
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getAllJobPosts();
    }

    public int updateJobPost(JobPost jobPost) {
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.updateJobPost(jobPost);
    }

    public void deleteJobPost(int id){
        JobPostDB jobPostDB = new JobPostDB(ctx);
        jobPostDB.deleteJobPost(id);
    }

    public int getJobPostsCount(){
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getJobPostCount();
    }

    //Add Category
    public void addCategory(Category category) {
        CategoryDB categoryDB = new CategoryDB(ctx);
        categoryDB.addCategory(category);
    }


    public Category getCategory(int id){
        CategoryDB categoryDB = new CategoryDB(ctx);
        return categoryDB.getCategory(id);
    }

    public List<Category> getAllCategories(){
        CategoryDB categoryDB = new CategoryDB(ctx);
        return categoryDB.getAllCategories();
    }

    public int getCategoriesCount(){
        CategoryDB categoryDB = new CategoryDB(ctx);
        return categoryDB.getCategoriesCount();
    }


    public void addSubcategory(Subcategory subcategory) {
        SubcategoryDB subcategoryDB = new SubcategoryDB(ctx);
        subcategoryDB.addSubcategory(subcategory);
    }

    public Subcategory getSubcategory(int id){
        SubcategoryDB subcategoryDB = new SubcategoryDB(ctx);
        return subcategoryDB.getSubcategory(id);
    }

    public List<Subcategory> getAllSubcategories(){
        SubcategoryDB subcategoryDB = new SubcategoryDB(ctx);
        return subcategoryDB.getAllSubcategories();
    }

    public int getSubcategoriesCount(){
        SubcategoryDB subcategoryDB = new SubcategoryDB(ctx);
        return subcategoryDB.getSubcategoriesCount();
    }


    public Boolean checkEmail(String email){
        UserDB userDB = new UserDB(ctx);
        return userDB.checkEmail(email);
    }

    public Boolean emailpassword(String email, String password){
        UserDB userDB = new UserDB(ctx);
        return userDB.emailpassword(email,password);
    }


    public String getUsername(){
        UserDB userDB = new UserDB(ctx);
        return userDB.getUsername();
    }


    public long getUserId (String email){
        UserDB userDB = new UserDB(ctx);
        return userDB.getUserId(email);
    }


    public int getSubcategoryId (String name){
       SubcategoryDB subcategoryDB = new SubcategoryDB(ctx);
       return subcategoryDB.getSubcategoryId(name);
    }

    public int getCategoryId (int id){
        CategoryDB categoryDB = new CategoryDB(ctx);
        return categoryDB.getCategoryId(id);
    }


    public List<JobPost> getSelectedJobPosts(int category_id) {
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getSelectedJobPosts(category_id);
    }


    public Boolean checkIfUsernameExists(String username) {
        UserDB userDB = new UserDB(ctx);
        return userDB.checkIfUsernameExists(username);
    }

    public Boolean checkIfEmailExists(String email) {
        UserDB userDB = new UserDB(ctx);
        return userDB.checkIfEmailExists(email);
    }

    public List<JobPost> getJoinedPosts(int category_id) {
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getJoinedPosts(category_id);
    }

    public void installCategories(){
        CategoryDB categoryDB = new CategoryDB(ctx);
        categoryDB.installCategories();
    }

    public List<JobPost> getJoinedSubcategoryPosts(int subcategory_id) {
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getJoinedSubcategoryPosts(subcategory_id);
    }

    public List<JobPost> getUserPost(int user_id){
        JobPostDB jobPostDB = new JobPostDB(ctx);
        return jobPostDB.getUserPost(user_id);
    }
    //favorites//

    public void addFavorite(Favorite favorite) {
       FavoriteDB favoriteDB = new FavoriteDB(ctx);
       favoriteDB.addFavorite(favorite);
    }

    public Favorite getFavorite(int id) {
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getFavorite(id);
    }

    public List<Favorite> getAllFavorites(){
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getAllFavorites();
    }

    public int updateFavorite(Favorite favorite) {
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.updateFavorite(favorite);
    }

    public void deleteFavorite(int id){
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        favoriteDB.deleteFavorite(id);
    }

    public Favorite getIsFavor(int postId){
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getIsFavor(postId);
    }

    public Favorite getIsFavorForDelete(int postId, int userId){
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getIsFavorForDelete(postId,userId);
    }

    public boolean getFav(int current_user_id, int job_post_id) {
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getFav(current_user_id, job_post_id);
    }

    public List<Favorite> getCurrentUserFavorites(int current_user_id){
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getCurrentUserFavorites(current_user_id);
    }


    public int getCurrentUserFav(int current_user_id, int job_post_id){
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getCurrentUserFav(current_user_id, job_post_id);
    }

    public List<JobPost> getCurrentUserFavoriteJobPosts(int user_id) {
        FavoriteDB favoriteDB = new FavoriteDB(ctx);
        return favoriteDB.getCurrentUserFavoriteJobPosts(user_id);
    }



    public void addReview(Review review) {
        ReviewDB reviewDB = new ReviewDB(ctx);
        reviewDB.addReview(review);
    }

    public Review getReview(int id) {
        ReviewDB reviewDB = new ReviewDB(ctx);
        return reviewDB.getReview(id);
    }

    public List<Review> getAllReviews(){
        ReviewDB reviewDB = new ReviewDB(ctx);
        return reviewDB.getAllReviews();
    }

    public int updateReview(Review review){
        ReviewDB reviewDB = new ReviewDB(ctx);
        return reviewDB.updateReview(review);
    }

    public void deleteReview(int id) {
        ReviewDB reviewDB = new ReviewDB(ctx);
        reviewDB.deleteReview(id);
    }

    public List<Review> getAllPostReviews(int postId) {
        ReviewDB reviewDB = new ReviewDB(ctx);
        return reviewDB.getAllPostReviews(postId);
    }

    public void addMessage(Message message) {
        MessageDB messageDB = new MessageDB(ctx);
        messageDB.addMessage(message);

    }

    public Message getMessage(int id) {
        MessageDB messageDB = new MessageDB(ctx);
        return messageDB.getMessage(id);
    }

    public List<Message> getAllMessages(){
        MessageDB messageDB = new MessageDB(ctx);
        return messageDB.getAllMessages();
    }

    public int updateMessage(Message message){
        MessageDB messageDB = new MessageDB(ctx);
        return messageDB.updateMessage(message);
    }

    public void deleteMessage(int id) {
        MessageDB messageDB = new MessageDB(ctx);
        messageDB.deleteMessage(id);
    }

    public List<Message> getSingleChatMessages(int user_id, int post_user_id) {
        MessageDB messageDB = new MessageDB(ctx);
        return messageDB.getSingleChatMessages(user_id, post_user_id);
    }

    public List<Message> getAllReceiverMessages(int receiver_id) {
       MessageDB messageDB = new MessageDB(ctx);
       return messageDB.getAllReceiverMessages(receiver_id);

    }

    public boolean verifyEmptyInboxForReceivers(int receiver_id) {
        MessageDB messageDB = new MessageDB(ctx);
        return messageDB.verifyEmptyInboxForReceivers(receiver_id);
    }



}